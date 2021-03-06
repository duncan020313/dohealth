# 운동하세요
*****
## 목적   
'운동하세요' 앱은 같은 목적을 가진 사람들을 하나의 그룹으로 묶어 운동 의지를 증폭시키기 위해 만들어졌습니다.   
혼자서 하는 운동은 그 의지가 쉽게 사라지지만 다른 사람과 함께 함으로써 서로가 경쟁자이자 팀이 되어 당신의 운동 의지를 더욱 강하게 만들어 줄 것입니다.   
## 앱 설명
### Tab 1 : 랭킹   
<img src = https://user-images.githubusercontent.com/58449555/125558493-b18c1a11-d838-446e-ad17-697b8ecf9804.jpg width="30%" height="30%">    



* 유저 별 랭킹 시스템을 도입해 유저들간의 경쟁으로 강한 운동 의지를 가질 수 있도록 했다.         
* 운동을 얼마나 꾸준히, 많이 했는지를 기준으로 순위를 매겼다.   



### Tab 2 : 운동 일지   
<img src = https://user-images.githubusercontent.com/58449555/125558488-8837f850-b925-434a-9f58-247bdfb958e9.jpg width="30%" height="30%"><img src = https://user-images.githubusercontent.com/58449555/125558495-7741f5ef-a26e-4ba0-9a11-a49487001eb9.jpg width="30%" height="30%">


* 오늘 한 운동을 기록할 수 있는 운동일지를 제작했다.   
* 세트 단위로 운동을 기록할 수 있다. 세트 수, 운동 볼륨, 최대 중량, 총 횟수를 기록해준다.   
* 한 세트가 끝나면 설정한 시간에 따라 타이머가 작동한다.   
* 운동의 추가 및 삭제는 Floatingactionbutton으로 할 수 있다.   


### Tab 3 : 운동 그룹 
<img src = https://user-images.githubusercontent.com/58449555/125558484-aa9e5da5-e76a-4a34-ada0-f05808eb38e1.jpg width="30%" height="30%"><img src = https://user-images.githubusercontent.com/58449555/125558491-a1e9018c-6a16-4a8d-b8ae-f966f9f81fba.jpg width="30%" height="30%"><img src = https://user-images.githubusercontent.com/58449555/125558476-7fd9922f-5d99-4587-9ed8-03573db35fdf.jpg width="30%" height="30%">


* 이 앱의 핵심 기능으로, 비슷한 운동 목적과 목표를 가진 사람들 끼리 그룹을 구성하여, 서로가 운동을 얼마나 했는지 볼 수 있다.   
* 또한, Progressbar로 그룹 전체의 운동 목표 달성 정도를 보여주어, 그룹 구성원간의 운동 의지를 강화시킨다.   
* 운동 목표 달성도, 운동 볼륨, 운동 달성도 등을 기준으로 그룹을 Sorting해서 보여준다.   
* 새로운 그룹을 생성할 수 있으며 운동 목표와 최대 인원수, 그룹 소개글을 설정할 수 있다.   


*****
## 앱 구현   
### Tab 1   
#### Frontend   
* Recyclerview를 사용해 서버에서 받은 상위 랭킹 유저들을 띄워줌   
#### Backend   
* DB에서 가장 많이 운동을 한 유저들을 sorting하여 앱으로 넘겨준다.   
### Tab 2
#### Frontend
* Calendarview를 사용해 각 날짜 별로 운동일지를 기록할 수 있도록 한다.
* Floatingactionbutton으로 운동을 추가/삭제 하고 타이머를 설정할 수 있도록 했다.
* Recyclerview의 운동을 터치하면 dialog를 통해 중량과 횟수를 기록할 수 있도록 했다.
* 중량과 횟수를 기록하고 나면 자동으로 타이머가 실행되도록 했다.
#### Backend
* 달력에서 날짜를 고를 때 마다 서버에 request를 보내 해당 날짜의 운동 일지를 가져온다.
### Tab 3
#### Frontend 
* 내 그룹을 볼 수 있는 Recyclerview와 Recyclerview의 마지막 item으로 추가 버튼을 만들었다.
* 내 그룹에 있는 그룹을 터치하면 그룹을 보여주는 Activity를 통해 그룹의 자세한 정보를 보여준다.
#### Backend
* 서버로 그룹 id를 전달하면 서버에서 유저 목록이나 그룹의 정보를 query하여 다시 앱으로 전달한다.
