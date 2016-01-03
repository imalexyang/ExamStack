package com.examstack.scoremarker;

import java.io.IOException;

import org.apache.commons.daemon.DaemonContext;
import org.apache.commons.daemon.DaemonInitException;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.stereotype.Component;

import com.examstack.common.daemon.AbstractDaemon;
import com.examstack.common.domain.exam.AnswerSheet;
import com.examstack.scoremarker.config.ScoreMarkConfig;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.rabbitmq.client.ConsumerCancelledException;
import com.rabbitmq.client.QueueingConsumer;
import com.rabbitmq.client.ShutdownSignalException;

/**
 * 
 * @author Ocelot
 *
 */
@Component
public class ScoreMarkerMain extends AbstractDaemon {

	@Autowired
	private QueueingConsumer consumer;

	private static final Logger LOGGER = Logger.getLogger(ScoreMarkerMain.class);
	private boolean waitingForMessage = false;
	@Autowired
	private ScoreCalcuService scoreCalcuService;
	
	@Autowired
	private ObjectMapper mapper;

	@Override
	public void init(DaemonContext arg0) throws DaemonInitException, Exception {

		context = new AnnotationConfigApplicationContext();
		context.register(ScoreMarkConfig.class);
		context.refresh();
		scoreCalcuService = context.getBean(ScoreCalcuService.class);
		consumer = context.getBean(QueueingConsumer.class);
		mapper = context.getBean(ObjectMapper.class);
		createWorkThread();
		LOGGER.info("ScoreMarker daemon init done.");
	}

	public static void main(String[] args) {

		AnnotationConfigApplicationContext ctx = new AnnotationConfigApplicationContext();
		ctx.register(ScoreMarkConfig.class);
		ctx.refresh();
		ScoreMarkerMain scoreMakerMain = ctx.getBean(ScoreMarkerMain.class);

		try {
			scoreMakerMain.workThread = Thread.currentThread();
			scoreMakerMain.run();
			LOGGER.info("ScoreMarker startup passed");
		} catch (Throwable e) {
			LOGGER.error("ScoreMarker startup failed.", e);
			return;
		} finally {
			ctx.close();
		}

	}

	@Override
	public void start() throws Exception {
		if (getWorkThread() != null) {
			getWorkThread().start();
			LOGGER.info("ScoreMarker daemon startup done.");
		} else {
			LOGGER.error("ScoreMarker daemon startup failed, work thread isn't initialized.");
		}

	}

	@Override
	public void stop() throws Exception {
		// TODO Auto-generated method stub

	}

	@Override
	protected void run() {
		while (!isShutdownRequested()) {
			try {
				LOGGER.info("scoreMaker checking next delivery from message queue");
				waitingForMessage = true;
				QueueingConsumer.Delivery delivery = consumer.nextDelivery();
				waitingForMessage = false;
				AnswerSheet answerSheet = mapper.readValue(delivery.getBody(),
						AnswerSheet.class);
				scoreCalcuService.calcuScore(answerSheet);
				
				
			} catch (ShutdownSignalException e) {
				LOGGER.error("scoreMaker received ShutdownSignalException: ", e);
				try {
					// Sleep some time to release the CPU time slices if
					// connection with RabbitMQ server
					// is disconnected, otherwise CPU usage will be high if
					// connection is disconnected.
					Thread.sleep(100);
				} catch (Throwable ex) {
					LOGGER.error("scoreMaker sleep exception: ", ex);
				}
			} catch (IOException | ConsumerCancelledException | InterruptedException e) {
				LOGGER.error("scoreMaker received exception", e);
			}
		}

	}

}
