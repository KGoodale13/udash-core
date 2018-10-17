package io.udash.rest

import com.avsystem.commons.rpc.StandardRPCFramework
import io.udash.rpc.JsonStr

trait UdashRESTFramework extends StandardRPCFramework {
  type RawValue = JsonStr
  type ParamTypeMetadata[T] = SimplifiedType[T]

  class ResultTypeMetadata[+T]
  implicit object ResultTypeMetadata extends ResultTypeMetadata[Nothing]

  def bodyValuesWriter: Writer[Map[String, RawValue]]
  def bodyValuesReader: Reader[Map[String, RawValue]]

  trait ValidREST[T]

  object ValidREST {
    def apply[T](implicit validREST: ValidREST[T]): ValidREST[T] = validREST
  }

  implicit def materializeValidREST[T]: ValidREST[T] = macro macros.RESTMacros.asValidRest[T]

  trait ValidServerREST[T]

  object ValidServerREST {
    def apply[T](implicit validREST: ValidServerREST[T]): ValidServerREST[T] = validREST
  }

  implicit def materializeValidServerREST[T]: ValidServerREST[T] = macro macros.RESTMacros.asValidServerRest[T]

  sealed trait SimplifiedType[T]
  object SimplifiedType {
    implicit object DoubleType extends SimplifiedType[Double]
    implicit object FloatType extends SimplifiedType[Float]
    implicit object LongType extends SimplifiedType[Long]
    implicit object IntType extends SimplifiedType[Int]
    implicit object CharType extends SimplifiedType[Char]
    implicit object ShortType extends SimplifiedType[Short]
    implicit object ByteType extends SimplifiedType[Byte]
    implicit object StringType extends SimplifiedType[String]
    implicit object BooleanType extends SimplifiedType[Boolean]
    class AnyRefSimplifiedType[T] extends SimplifiedType[T]
    implicit def anyRefSimplifiedType[T]: SimplifiedType[T] = new AnyRefSimplifiedType[T]
  }
}