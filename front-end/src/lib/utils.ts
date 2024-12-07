import { type ClassValue, clsx } from "clsx";
import { twMerge } from "tailwind-merge";

export function cn(...inputs: ClassValue[]) {
	return twMerge(clsx(inputs));
}

export const formatCurrency = (amount: number): string => {
	return Intl.NumberFormat("pt-BR", {
		style: "currency",
		currency: "BRL",
	}).format(amount);
};

export const formatDate = (rawDate: string): string => {
	const date = new Date(rawDate);
	return Intl.DateTimeFormat("pt-BR", {}).format(date);
};
