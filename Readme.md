Belley Rani Bala - selenium + JUnit 5 testing of hudl.com
This project can be also found here: https://github.com/BelleyRaniBala/HudlTest.git

This is a maven project. You need JDK8 to run it.

When you have your maven ready run:
mvn clean package

if you don't have maven ready, you can try the included maven using:
mvn clean package

That will execute all Login tests inside of the framework.
I run my test from IntelliJ comunity edition on Windows.

In case you would like to use firefox driver, please change the details in the com.hudl.Launcher class.


The Feedback received:

UI Project
No defensive code to try and protect against a WebElement not being on the page.
Missing some of the more advanced uses of Selenium
The Page Object Model used was good and uses inheritance to not repeat code and gives a good foundation to work from.
The parameterisation of Junit allows for some data driven testing, but no explanation of what these parameter will do. Potentially BDD should have been used instead. 