package io.udash.rpc

trait RPCBackend {
  type DefaultAtmosphereFramework = io.udash.rpc.utils.DefaultAtmosphereFramework
  type FileDownloadServlet = io.udash.rpc.utils.FileDownloadServlet
  type FileUploadServlet = io.udash.rpc.utils.FileUploadServlet
}