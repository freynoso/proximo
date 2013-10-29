Proximo
-------
Alternative real time web interface for traccar http://www.traccar.org/.

###Tech overview
- Java Play! Framework based project.
- Akka remote actors to recive events from traccar.
- Boostrap 3 GUI with Server Sent Events (modern browsers only -and not IE yet-).

###Current status
This is a work in progress. It is **NOT** production ready. Devices/Users must be added manually. Also, the auth system needs to be improved (cookies expose the logged user).

Install
-------
#### Install Traccar
Proximo needs a patched version of traccar (proximo branch) avalible at https://github.com/freynoso/traccar
```sh
cd 
git clone --branch=proximo https://github.com/freynoso/traccar
cd traccar
mvn install
```
#### Install Play! Framework 2.2.x
Instructions at http://www.playframework.com/documentation/2.2.x/Installing

#### Install Proximo
```sh
cd
git clone https://github.com/freynoso/proximo.git
```

Config
------
#### Config Traccar
http://www.traccar.org/docs/config.jsp

And add the following lines:
```XML
    <!-- Akka Actor -->
    <entry key='akkaActor.enable'>true</entry>
    <entry key='akkaActor.config'>
    	akkaActor {
			akka {
				actor {
					provider = "akka.remote.RemoteActorRefProvider"
				}
				remote {
					enabled-transports = ["akka.remote.netty.tcp"]
 					netty.tcp {
   						hostname = "127.0.0.1"
   						port = 2553
   					}
   				}
   			}
		}
    </entry>
    <entry key='akkaActor.remoteActorPath'>akka.tcp://application@127.0.0.1:2552/user/map</entry>
```
A compleate default.cfg file is avalible at [proximo/lib/default.cfg](https://github.com/freynoso/proximo/blob/master/lib/default.cfg).

#### Config Proximo
- Database config: proximo/conf/application.conf
- Initial data (users and devices): proximo/conf/initial-data.yml 

Run
---
#### Run traccar
```sh
cd ~/traccar
sudo java -jar target/tracker-server.jar default.cfg
```

#### Run Proximo
```sh
cd ~/proximo
play run
```
(Make sure Play! Framework is in your PATH).

Open you browser and go to http://localhost:9000/.

User/pass: admin/admin -or- demo/demo.


GPS device simulator
-------------------
#### Install gpsbabel
```sh
sudo apt-get install gpsbabel
```
#### Run demo
```sh
cd ~/proximo/tools
./demo.sh 123456789012345 sample1.gpx
```
You could also open another shell and simulate a different device/gps trace.
```sh
cd ~/proximo/tools
./demo.sh 999999999999999 sample2.gpx
```

License
----
```
   Copyright 2013 Francisco Omar Reynoso <franole@gmail.com>

   Licensed under the Apache License, Version 2.0 (the "License");
   you may not use this file except in compliance with the License.
   You may obtain a copy of the License at

       http://www.apache.org/licenses/LICENSE-2.0

   Unless required by applicable law or agreed to in writing, software
   distributed under the License is distributed on an "AS IS" BASIS,
   WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
   See the License for the specific language governing permissions and
   limitations under the License.
```
