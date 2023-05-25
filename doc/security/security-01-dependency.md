# SpringSecurity 설치 

SpringBoot 버전은 다음과 같다. 
* SpringBoot 3.1.0 
* SpringSecurity 6.1.0 

**pom.xml**   
```xml
<?xml version="1.0" encoding="UTF-8"?>
<project>
	<modelVersion>4.0.0</modelVersion>
	<parent>
		<groupId>org.springframework.boot</groupId>
		<artifactId>spring-boot-starter-parent</artifactId>
		<version>3.1.0</version>
		<relativePath/> <!-- lookup parent from repository -->
	</parent>
	
	<dependencies>
    <!-- 나머지는 생략 -->
		<!-- Spring Security -->
		<dependency>
				<groupId>org.springframework.boot</groupId>
				<artifactId>spring-boot-starter-security</artifactId>
		</dependency>
	</dependencies>
</project>
```