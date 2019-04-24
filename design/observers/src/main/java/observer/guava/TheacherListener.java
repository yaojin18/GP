package observer.guava;

import com.google.common.eventbus.Subscribe;

public class TheacherListener {

    @Subscribe
    public void noticeTheacher(QuestionBean qb){
        System.out.println(qb.getQuestioner()+"在GPer社区提问了，请"+qb.getAsker()+"尽快解答，问题是："+qb.getQuestion());
    }
}
