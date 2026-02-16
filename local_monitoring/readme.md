# Docker Runtime Prometheus/Grafana

The `docker-compose.yaml` in this folder provides a full set of applications to run a Stack of 
SpringActuator/Prometheus/Grafana for monitoring your application.

use a terminal/console to start the Docker containers
### starting
first start your application, then   
start with: `docker-compose up`  

### use
Use these URLs and credentials to reach the applications:  
**Prometheus:** http://localhost:9090 (no user/password)   
A ready-to-use configuration is already provided with the `prometheus.yml`. It will scrape your application metrics 
every 30 seconds.   
**Grafana:** http://localhost:3000 (admin:admin)  
with the very first start you will need to add the prometheus container as a datasource. 
See the Grafana documentation for instructions. After that you can set up a dashboard    
TIPP: the Grafana marketplace provides a lot of ready to use dashboard configs you can import https://grafana.com/grafana/dashboards/

### stopping
stop with: `docker-compose down`  