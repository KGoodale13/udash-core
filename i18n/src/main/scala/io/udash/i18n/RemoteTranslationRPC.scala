package io.udash.i18n

import io.udash.rpc.DefaultRpcCompanion

import scala.concurrent.Future

/** RPC interface for Udash i18n handling on server-side. */
trait RemoteTranslationRPC {
  /** Returns text to replace translation key. */
  def loadTemplate(key: String)(implicit lang: Lang): Future[String] =
    loadTemplateForLang(lang, key)

  /** Returns map of translations and bundle hash. If `oldHash` is not outdated, this Future will contain None. */
  def loadTranslations(oldHash: BundleHash)(implicit lang: Lang): Future[Option[Bundle]] =
    loadTranslationsForLang(lang, oldHash)

  /** Returns text to replace translation key. */
  def loadTemplateForLang(lang: Lang, key: String): Future[String]

  /** Returns map of translations and bundle hash. If `oldHash` is not outdated, this Future will contain None. */
  def loadTranslationsForLang(lang: Lang, oldHash: BundleHash): Future[Option[Bundle]]
}
object RemoteTranslationRPC extends DefaultRpcCompanion[RemoteTranslationRPC]
