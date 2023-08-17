# User Server
### Swagger 적용
* application.yml Setting(차후 Swagger 보완 예정 - userService 말고 가장 바깥 README로 옮길 예정)
  ```
  ```
* build.gradle<br>
`implementation 'org.springdoc:springdoc-openapi-starter-webmvc-ui:2.1.0'`

  ><b>⚠️주의</b><br>
  [사진](사진)<br>
  더이상 해당 의존성 주입을 지원하지 않음<br>
` implementation 'org.springdoc:springdoc-openapi-ui:1.7.0'`


* <b>접속</b>
  > 1. swagger 접속<br>
      <b>http://server:port/swagger-ui/index.html<b><br>
      ex) `http://localhost:8000/swagger-ui/index.html
  > 2. json 형식으로 출력<br>
      <b>http://server:port/v3/api-docs<b><br>
      ex) `http://localhost:8000/v3/api-docs`
  > 3. yaml 파일 다운<br>
      <b>http://server:port/v3/api-docs.yaml</b><br>
      ex) `http://localhost:8000/v3/api-docs.yaml`


### TDD Test
* @WebMvcTest 사용
    > <b>@WebMvcTest</b><br>
    > * @springBootTest와는 달리 Controller와 특정 어노테이션만 빈에 등록<br>
        따라서 사용하는 모든 @Service는 각각 모두 @MockBean 을 사용하여 추가해 주어야 함
    > * Mock 객체(가짜 객체)이므로 실제와는 차이가 발생할 수 있음
    > * 단위 테스트로 사용하는 경우가 대부분<br> 이유는 차후 작성. 예상은 Controller에서 호출하는 Service단 까지밖에 실행안함. 즉 DB을 안거침
    > * 통합 테스트의 경우 전체 빈을 등록하는 SpringBootTest가 적합
* 사용법 - 이건 제가 너무 헷갈렸어서 적어놨어요...!
1. @WebMvcTest(테스트할 컨트롤러 명.class) 추가
    ```
    @WebMvcTest(UserController.class)
    ```
2. Controller에서 호출하고 있는 각 서비스 빈에 등록(만일 여러개의 서비스 참조시 모두 등록)
    ```
    @MockBean
    UserService userService;
    ```
3. @Transatinal 제거<br>
    Controller만 실행하기 때문에 그와 연결된 Service 단까지만 실행(예상)<br>
    DB까지 갔다오지 않기 때문에 Transation 오류 발생<br>
    나중에 조회 소스 추가되면 확인 예정