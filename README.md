# 🙌 프로젝트 설명
- 간단한 게시판을 구현하였습니다.
- Github Actions, Java 21 , Prometheus , Grafana , Ngrinder , Spring security , swagger doc , Spring JPA , EC2 , RDS
- Ngrinder를 통한 부하테스트와 이를 개선하는 것을 주 목적으로 하였습니다.
- API 는 기본적인 게시판 CRUD 와 로그인 회원가입이 있으며 JWT 토큰을 통한 인증 방식을 사용합니다.
- swagger api docs : http://ec2-43-203-123-63.ap-northeast-2.compute.amazonaws.com:8080/swagger-ui/index.html

## API 도메인 목록
- ### 회원
  - 회원가입
  - 로그인
- ### 글
  - 리스트 조회 및 페이징
  - 글 삽입
  - 글 수정
  - 글 삭제
  - 글 detail 조회 with 댓글
- ### 댓글
  -  삽입
  -  수정

## 🙋‍♀️ 테스트 로그
vUser 999명을 기준으로 테스트 진행 시 task pool 의 사이즈를 170 이상으로 하여야 했습니다.
이후 테스트를 스레드 10부터 늘려보는 방식으로 진행하였습니다.
테스트는 한 대의 로컬PC에 server, db , ngrinder 를 사용하여 진행하였습니다.
![image](https://github.com/user-attachments/assets/e91d114a-d115-4d61-a9d3-ba762a6f3361)
![image](https://github.com/user-attachments/assets/02bef0cd-c8e9-4c05-8a6a-108986cbc08d)
이렇게 스레드를 늘리고 테스트를 진행하면서 데이터를 30만건을 넘게 insert 하기도 하였습니다.
그렇게 데이터가 70만건까지 되니 문제를 발견하였습니다.

### [부하 테스트 성능 개선 과정](https://desert-elk-95d.notion.site/4e04fa73404d4cd8897b47eb08f75729?pvs=4)
### [프로젝트 설명]https://www.notion.so/6e6df78c5eaa41188fc20aa5477947b7
