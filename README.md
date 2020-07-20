# Git Blog 기본 기능 구현

## 참여자
- 홍창경(팀장) @dominicong
- 김채은 @fairy037
- 박진수 @ahrtzzinn
- 오지환 @ojhoshlmw
- 전태준 @jeon3029

## 개발 Ground Rule
1. __branch 종류__
  - __develop-_[이니셜]___ : 각 개발자들이 작업하는 개인 공간.
  - __develop__ : 팀에 대응하는 개발용 branch. (반드시 각 개발자들은 merge하기 전에 팀원들에게 공지하고 merge할 것!)
  - __master__ : Service Release 및 제출용 branch(결과물). (Merge할 경우 Request 필수!)
   
2. __Commit 메세지 Format__  
  ___"[type] commit message, issue Key"___  
  _ex) git commit -m "[Add]<기능설명>, jira Key"_
  - __Add :__ 새로운 기능 추가.
  - __Fix :__ 버그 수정.
  - __Modify :__ 기능에 버그는 없지만, 코드 수정.
  - __Test :__ 테스트용 코드.
  - __Style :__ 단순 코드 포멧팅.(세미콜론 누락, 들여쓰기 등).
  - __Doc :__ 문서(.md 등) 수정.


## 구현 내용
- 에디터 작성(마크다운 기반)
  - 포스팅 된 글 파일로 export
  - 빠른 이미지 삽입
- git commit 내역에 대한 포스트 기능
  - 글 작성에 대한 꽃심기(UI)
- 포트폴리오 관리
  - 프로젝트별 블로그 포스팅 내역
