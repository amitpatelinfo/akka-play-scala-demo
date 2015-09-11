package com.typesafe.reactive.workshop;

import akka.actor.AbstractLoggingActor;
import akka.actor.ActorRef;
import akka.actor.Props;
import akka.japi.pf.ReceiveBuilder;
import scala.PartialFunction;
import scala.concurrent.duration.FiniteDuration;
import scala.runtime.BoxedUnit;

import java.io.Serializable;
import java.util.concurrent.TimeUnit;

public class Barkeeper extends AbstractLoggingActor {

  private final FiniteDuration prepareDrinkDuration = new FiniteDuration(2, TimeUnit.SECONDS);

  public static Props props() {
    return Props.create(Barkeeper.class, () -> new Barkeeper());
  }

  public static final class PrepareDrink implements Serializable {

    public final Drink drink;
    public final ActorRef guest;

    public PrepareDrink(Drink drink, ActorRef guest) {
      assert drink != null;
      this.drink = drink;
      this.guest = guest;
    }

  }

  public static final class DrinkPrepared implements Serializable {

    public final Drink drink;
    public final ActorRef guest;

    public DrinkPrepared(Drink drink, ActorRef guest) {
      this.drink = drink;
      this.guest = guest;
    }

    @Override
    public boolean equals(Object o) {
      if (this == o) return true;
      if (o == null || getClass() != o.getClass()) return false;

      DrinkPrepared that = (DrinkPrepared) o;

      return !(drink != null ? !drink.equals(that.drink) : that.drink != null);

    }

    @Override
    public int hashCode() {
      return drink != null ? drink.hashCode() : 0;
    }
  }

  @Override
  public PartialFunction<Object, BoxedUnit> receive() {
    return ReceiveBuilder
            .match(PrepareDrink.class, sd -> {
              Utils.busy(prepareDrinkDuration);
              sender().tell(new DrinkPrepared(sd.drink, sd.guest), self());
            })
            .build();
  }
}
