package observer.guava;

import com.google.common.eventbus.EventBus;

public class Question {
    public static void main(String[] args) {
        EventBus eventBus = new EventBus();
        eventBus.register(new TheacherListener());
        QuestionBean qb = new QuestionBean();
        qb.setAsker("Tom");
        qb.setQuestion("多线程的底层原理是什么？");
        qb.setQuestioner("yaojin");
        eventBus.post(qb);
    }
}
