<script setup lang="ts">
import {
	Table,
	TableBody,
	TableCaption,
	TableCell,
	TableHead,
	TableHeader,
	TableRow,
} from "@/components/ui/table";
import { Transfer } from "@/@types";
import { formatDate, formatCurrency } from "@/lib/utils";

defineProps<{
	transfers: Transfer[];
}>();
</script>

<template>
  <Table>
    <TableCaption>Sua lista de Transferências recentes</TableCaption>
    <TableHeader>
      <TableRow>
        <!-- <TableHead>ID</TableHead> -->
        <TableHead>Valor</TableHead>
        <TableHead>Conta Origem</TableHead>
        <TableHead>Conta Destino</TableHead>
        <TableHead>Agendado para</TableHead>
        <TableHead>Agendado em</TableHead>
        <TableHead>Tarifa fixa (R$)</TableHead>
        <TableHead>Tarifa em %</TableHead>
        <TableHead>Criado em</TableHead>
      </TableRow>
    </TableHeader>
    <TableBody>
      <TableRow v-for="transfer in transfers" :key="transfer.id">
        <!-- <TableCell class="font-medium">{{ transfer.id }}</TableCell> -->
        <TableCell>{{ transfer.amount }}</TableCell>
        <TableCell>{{ transfer.sourceAccount }}</TableCell>
        <TableCell>{{ transfer.destinationAccount }}</TableCell>
        <TableCell>{{ formatDate(transfer.scheduledDate) }}</TableCell>
        <TableCell>{{ formatDate(transfer.transferDate) }}</TableCell>
        <TableCell>{{ formatCurrency(transfer.fee.fixedFee) }}</TableCell>
        <TableCell>{{(transfer.fee.percentageFee * 100).toFixed(2)}} %</TableCell>
        <TableCell>{{ formatDate(transfer.createdAt) }}</TableCell>
      </TableRow>
    </TableBody>
  </Table>
</template>

