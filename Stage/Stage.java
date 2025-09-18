package Stage;

import Actor.Actor;
import Actor.HappyActor;
import Actor.SadActor;

class Stage {
    Actor a = new HappyActor();
    void change() {
        a = new SadActor();
    }
    void go() {
        a.act();
    }
}
