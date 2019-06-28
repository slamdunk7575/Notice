## Explanation
My Notice Board Project.

## Functions
Basic CRUD.

File Upload & Download.

Paging. (Use Spring Pageable Interface)

Etc.     
AOP(Logging, Transaction, Exception),

Interceptor, Filter(한글처리)


## How To Build & Run
1. 프로젝트를 Clone or Download 합니다.
2. 터미널을 실행시켜 해당 폴더로 이동.
~~~
3. $ gradle bootjar
~~~

build/libs 경로에 가보면 모든 의존성 라이브러리가 포함된 jar 파일을 확인 할 수 있음.

~~~
4. nohup java -jar notice-0.0.1-SNAPSHOT.jar
5. tail -f nohup.out &
~~~

Browser에서 http://localhost:8080/api/noticePage/ 접속. 

## Dev Env
JAVA: 1.8

Spring Boot: 2.1.6

ORM: Spring Data JPA

Front: Tymeleaf, Jquery, HTML, CSS -> Vue.JS (Change later)

DB: MySQL ->  -> H2

Build: Gradle

IDE: IntelliJ

Versioning: GitHub

OS: MacOS

## TODO
Spring Data Redis, Lettuce, Embedded Redis를 사용해보려고 했으나 Pageable에서 정렬이 안되는 문제가 있었다. 
Pageable을 사용하지 않고 직접 구현해서 적용해보기.

로그인(권한/인증) 처리하기.

Swagger 적용하기.

TEST 코드 추가하기.

Front를 Vue.js로 바꿔보기.




