package samples

import scala.language.implicitConversions
import scalaz._
import Scalaz._

//http://eed3si9n.com/learning-scalaz-day1
object Samples1 {
  def equalSamples() = {
    1 === 1
    ///1==="foo"
    1 == "foo"
    1.some =/= 2.some
    1 assert_=== 2
  }

  def orderSamples() = {
    1 > 2.0
    //1 gt 2.0
    1.0 ?|? 2.0
    1.0 max 2.0
  }

  def showSamples() = {
    3.show
    3.shows
    "hello".println
  }

  def enumSamples() = {
    'a' to 'e'
    'a' |-> 'e'
    3 |=> 5
    'B'.succ
  }

  def boundedSamples() = {
    implicitly[Enum[Char]].min
    implicitly[Enum[Char]].max
    //implicitly[Enum[Double]].max
    //implicitly[Enum[(Boolean, Int, Char)]].max
  }

  def typeclassesEqual() = {
    case class TrafficLight(name: String)
    val red = TrafficLight("red")
    val yellow = TrafficLight("yellow")
    val green = TrafficLight("green")
    implicit val TrafficLightEqual: Equal[TrafficLight] = Equal.equal(_ == _)
    red === yellow
  }

  trait CanTruthy[A] { self =>
    /** @return true, if `a` is truthy. */
    def truthys(a: A): Boolean
  }

  object CanTruthy {
    def apply[A](implicit ev: CanTruthy[A]): CanTruthy[A] = ev
    def truthys[A](f: A => Boolean): CanTruthy[A] = new CanTruthy[A] {
      def truthys(a: A): Boolean = f(a)
    }
  }
  trait CanTruthyOps[A] {
    def self: A
    implicit def F: CanTruthy[A]
    final def truthy: Boolean = F.truthys(self)
  }
  object ToCanIsTruthyOps {
    implicit def toCanIsTruthyOps[A](v: A)(implicit ev: CanTruthy[A]) =
      new CanTruthyOps[A] {
        def self = v
        implicit def F: CanTruthy[A] = ev
      }
  }

  def typeclassesSample() = {
    import ToCanIsTruthyOps._

    implicit val intCanTruthy: CanTruthy[Int] = CanTruthy.truthys({
      case 0 => false
      case _ => true
    })
    10.truthy

    implicit def listCanTruthy[A]: CanTruthy[List[A]] = CanTruthy.truthys({
      case Nil => false
      case _ => true
    })
    List("foo").truthy

    implicit val nilCanTruthy: CanTruthy[scala.collection.immutable.Nil.type] = CanTruthy.truthys(_ => false)
    Nil.truthy

    implicit val booleanCanTruthy: CanTruthy[Boolean] = CanTruthy.truthys(identity)

    def truthyIf[A: CanTruthy, B, C](cond: A)(ifyes: => B)(ifno: => C) = {
      if (cond.truthy) ifyes
      else ifno
    }
    truthyIf(Nil) { "YEAH!" } { "NO!" }
    truthyIf(2 :: 3 :: 4 :: Nil) { "YEAH!" } { "NO!" }
    truthyIf(true) { "YEAH!" } { "NO!" }

  }
}