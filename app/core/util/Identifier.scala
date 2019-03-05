package core.util

trait Identifier[+A] {

  def value: A
  def isPersisted: Boolean

  override def equals(obj: Any) = obj match {
    case that: Identifier[_] =>
      value == that.value
    case _ => false
  }

  override def hashCode: Int = 31 * value.##

}