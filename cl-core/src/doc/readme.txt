TODO:

-High:
Async execute actions.

-High:
Add list view for EP.

-Done:
Add feature,filter data table by column search box.

-High:
Add chart for metric calculated by function.

-Low
Add split layout for views manager.

-Done:
Add note and book-mark for chart

-Midd:
Instead of type by command line, ConsoleOp should be select-able 
from the actions pane, when select each corp in the entity list 
view.

RUN:
$ mvn package -DskipTests
$ mvn exec:java -Dexec.mainClass=org.cellang.collector.CollectorMain -Dcellang.data.dir=/c/d/data
$ mvn exec:java -Dexec.mainClass=org.MainPanel -Dcellang.data.dir=/c/d/data
 