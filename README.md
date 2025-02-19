# secure-spring-api-starter
스프링 프로젝트 템플릿

[TODO]
1. jwt 토큰 + 로그인 + 스프링 시큐리티 + oauth2 로그인 적용
   - 특히 oauth2 로그인 시, "행복한 고양이82"와 같이 임의의 닉네임 자동생성 및 저장

2. [be] 와 [fe] vite-react 로그인 연동
   - be : 백엔드 루트 폴더
   - fe : 프론트 루트 폴더

3. CRUD 기본 세팅 
   - 패키지 순환 참조 방지를 위한 서비스 로직 분리
   - 예) Post 도메인
     - PostService : PostRepository만 의존성 주입 (단순 게시글 CRUD 로직)
     - PostUseCase : 다른 도메인 (User, Comment 등)의 Service가 필요한 복합적인 로직 처리
       - 예: 게시글 작성 시 사용자 정보 확인, 댓글 알림 등..

4. test-code 작성법 / Rest-Docs 연동
   - [spring rest docs] 테스트코드를 통한 Rest 문서 만들기
