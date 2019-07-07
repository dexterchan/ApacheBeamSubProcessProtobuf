


## Dataflow
build
````
gradle -Pdataflow clean build
````
````
export GOOGLE_APPLICATION_CREDENTIALS=/Users/dexter/.ssh/pigpig/gcp.serviceacct.peer2peer-67bc368759d4.json
# Gradle build
java -classpath build/libs/ApacheBeamSubProcessProtobuf-1.0-SNAPSHOT.jar org.apache.beam.examples.subprocess.ExampleEchoPipeline \
	--runner=DataflowRunner \
  --project=peer2peer \
  --sourcePath=gs://pi_calculation/echo/linux --concurrency=2 \
  --output=gs://pi_calculation/echo_df_dataflow \
  --tempLocation=gs://pi_calculation/temp/ \
  --region=us-central1 

````

