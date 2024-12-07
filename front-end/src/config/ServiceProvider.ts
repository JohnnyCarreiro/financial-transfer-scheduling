import { Transfer } from "@/@types";
import { TransferService } from "@/services/TransferService";
import { AxiosHttpClient } from "@/services/utils/AxiosHttpClinent";

const httpClient = new AxiosHttpClient({
	baseURL: "http://localhost:8080/api/v1",
});
const transferService = new TransferService<Transfer>(httpClient, "/transfers");

export { transferService };
