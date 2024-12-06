export interface ITransferService<T> {
	listTransfers(): Promise<T[]>;
	getTransferById(id: string): Promise<T>;
	createTransfer(payload: Partial<T>): Promise<T>;
	updateTransfer(id: string, payload: Partial<T>): Promise<T>;
	deleteTransfer(id: string): Promise<void>;
}
