import { Observable } from 'rxjs';
import { UploadedFileBean } from '../models/uploaded-file-bean';
import {AuthContext} from "../models/auth-context";

export interface FileService {
  readonly fileInfo: (filename: string, authContext: AuthContext) => Observable<UploadedFileBean>;
  readonly fetchFile: (filename: string, authContext: AuthContext) => Observable<unknown>;
  readonly uploadFile: (file: File, authContext: AuthContext) => Observable<UploadedFileBean>;
}
