package org.jpos.iso.channel;

import com.ibm.mq.jms.MQQueue;
import com.ibm.mq.jms.MQQueueConnection;
import com.ibm.mq.jms.MQQueueConnectionFactory;
import com.ibm.mq.jms.MQQueueReceiver;
import com.ibm.mq.jms.MQQueueSender;
import com.ibm.mq.jms.MQQueueSession;
import java.io.IOException;
import javax.jms.JMSException;
import org.jpos.core.Configurable;
import org.jpos.core.Configuration;
import org.jpos.core.ConfigurationException;
import org.jpos.iso.BaseChannel;
import org.jpos.iso.ISOChannel;
import org.jpos.iso.ISOMsg;

public class JMSChannel
  extends BaseChannel
  implements ISOChannel, Configurable
{
  public MQQueueConnectionFactory cf;
  public MQQueueConnection connection;
  public MQQueueSession session;
  public MQQueueSender sender;
  public MQQueueReceiver receiver;
  
  public void setConfiguration(Configuration paramConfiguration) throws ConfigurationException {
    super.setConfiguration(paramConfiguration);
    try {
      this.cf = new MQQueueConnectionFactory();
      this.cf.setHostName(paramConfiguration.get("host"));
      this.cf.setPort(paramConfiguration.getInt("port"));
      this.cf.setIntProperty("XMSC_WMQ_CONNECTION_MODE", 1);
      this.cf.setQueueManager(paramConfiguration.get("queueManager"));
      this.cf.setChannel(paramConfiguration.get("channel"));
      this.cf.setStringProperty("XMSC_USERID", paramConfiguration.get("user"));
      this.cf.setStringProperty("XMSC_PASSWORD", paramConfiguration.get("pass"));
      
      this.connection = (MQQueueConnection)this.cf.createQueueConnection();
      this.session = (MQQueueSession)this.connection.createQueueSession(false, 1);
      MQQueue mQQueue1 = (MQQueue)this.session.createQueue(paramConfiguration.get("pqname"));
      MQQueue mQQueue2 = (MQQueue)this.session.createQueue(paramConfiguration.get("gqname"));
      this.sender = (MQQueueSender)this.session.createSender(mQQueue1);
      this.receiver = (MQQueueReceiver)this.session.createReceiver(mQQueue2);
      
      this.connection.start();
    
    }
    catch (JMSException jMSException) {
      throw new ConfigurationException(jMSException);
    } 
  }


    public void send(ISOMsg paramISOMsg) throws IOException { (new Thread(() -> {

        })).start(); }
}
