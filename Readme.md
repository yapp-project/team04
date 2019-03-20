# YAPP 14기 4조 프로젝트

------

## 기초 Architecture

1. [java clean architecture - mvvm](https://github.com/googlesamples/android-architecture/tree/todo-mvvm-live/) 참고
   - 팀내 Java 전공자가 대부분이므로, Java 기반으로 로직 구성
   - AAC ViewModel (이하 ViewModel) 사용
   - ViewModel 내에서 context 사용하지 않음
   - ViewModel에서 View를 갱신할 때, LiveData 또는 DataBinding을 활용해서 알림
   - View와 ViewModel의 의존성을 줄임
     ex> ViewModel 내의 데이터가 수정되더라도, View 수정 내용이 최대한 없도록 설계
   - onDestroy() 동작 시, ViewModel 내부의 onCleared() 호출을 통해 Release 유도
2. DataBinding
   - 팀원이 Java가 대부분이므로 Java 기반으로 로직 구성
3. Kotlin
   - 사용 가능한 팀원에 따라 Kotlin 파일 사용할 수 있도록 함
4. LiveData
   - LiveData를 통한 observe 
5. 추후 활용 예정 기능
   - Retrofit
   - RxJava

## 앱내 사용 기능 및 컴포넌트

1. 캘린더
2. 



## 참고 Reference

1. https://thdev.tech/androiddev/2018/08/05/Android-Architecture-Components-ViewModel-Inject/ 