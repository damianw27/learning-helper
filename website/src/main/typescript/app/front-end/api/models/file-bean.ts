import { UploadedFileBean } from './uploaded-file-bean';

export interface FileBean {
	readonly uploadedFileBean: UploadedFileBean;
	readonly content: number[];
}
