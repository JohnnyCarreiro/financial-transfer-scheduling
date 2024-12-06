export interface ApiResponse<T> {
	data: T;
	success: boolean;
	message?: string;
}

export interface PaginationResponse<T> {
	items: T[];
	total: number;
	page: number;
	size: number;
}

export interface Transfer {
	id: string;
	sourceAccount: string;
	destinationAccount: string;
	amount: number;
	scheduledDate: string;
	transferDate: string;
	fee: { percentageFee: number; fixedFee: number };
	createdAt: string;
	updatedAt: string;
}
