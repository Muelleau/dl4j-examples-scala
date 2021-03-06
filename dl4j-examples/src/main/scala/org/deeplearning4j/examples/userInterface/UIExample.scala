package org.deeplearning4j.examples.userInterface

import org.deeplearning4j.examples.userInterface.util.UIExampleUtils
import org.deeplearning4j.ui.api.UIServer
import org.deeplearning4j.ui.stats.StatsListener
import org.deeplearning4j.ui.storage.InMemoryStatsStorage

/**
  * A simple example of how to attach Deeplearning4j's training UI to a network
  *
  * To change the UI port (usually not necessary) - set the org.deeplearning4j.ui.port system property
  * i.e., run the example and pass the following to the JVM, to use port 9001: -Dorg.deeplearning4j.ui.port=9001
  *
  * @author Alex Black
  */
object UIExample {

  def main(args: Array[String]) {

    //Get our network and training data
    val net = UIExampleUtils.getMnistNetwork
    val trainData = UIExampleUtils.getMnistData

    //Initialize the user interface backend
    val uiServer = UIServer.getInstance

    //Configure where the network information (gradients, activations, score vs. time etc) is to be stored
    //Then add the StatsListener to collect this information from the network, as it trains
    val statsStorage = new InMemoryStatsStorage //Alternative: new FileStatsStorage(File) - see UIStorageExample
    val listenerFrequency: Int = 1
    net.setListeners(new StatsListener(statsStorage, listenerFrequency))

    //Attach the StatsStorage instance to the UI: this allows the contents of the StatsStorage to be visualized
    uiServer.attach(statsStorage)

    //Start training:
    net.fit(trainData)

    //Finally: open your browser and go to http://localhost:9000/train
  }
}
