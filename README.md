# Webtoon Crawler
*The crawler that downloads Naver, Daum, and NateOn webtoons.*

## Overview
Webtoon Crawler is a GUI-based program currently only supports downloading from Naver.

My goal is to support webtoon downloading from Daum and NateOn also.

Note that I ***didn't / couldn't receive permission for this program from any of the three vendors
since none of them provided Webtoon API***.

The program looks as below.

![Naver Crawler](src/main/resources/images/naver_crawler.png)

## Technology used
- [Java 7](http://www.oracle.com/technetwork/java/javase/downloads/jdk7-downloads-1880260.html)
- [Maven](http://maven.apache.org/)
- [JSoup 1.7.3](http://jsoup.org/)

## Version Log
### [v1.0](https://github.com/TurtleShip/webtoon-crawler/tree/v1.0)
- Supports webtoon downloads from Naver.

## How to run this program
1. Download [jar file](https://github.com/TurtleShip/webtoon-crawler/raw/master/webtoon-crawler.jar)

2. Do one of the below depending on your OS
   - Windows : Double click the far file.
   - Mac, Linux : Type 'java -jar webtoon-crawler.jar' from the console.

## How to compile on your own
Run the below commands from your terminal/console

1. Download my source code.

   ```git clone git@github.com:TurtleShip/webtoon-crawler.git```

2. Use maven to create jar file.
   ```
cd webtoon-crawler
mvn package
   ```

3. Find and run the executable webtoon-crawler.jar you just created.
   ```
cd target
java -jar webtoon-crawler.jar
   ```

## License
[MIT License](license.txt)

## Credit
Inspired by a project [web_crawler](https://github.com/emeraldsnail/webtoon_crawler) developed by [**emeraldsnail**](https://github.com/emeraldsnail).

