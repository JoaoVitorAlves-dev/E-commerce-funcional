package ecom.merce.ecommerce.mapper;

import ecom.merce.ecommerce.dto.request.PagamentoRequest;
import ecom.merce.ecommerce.dto.response.PagamentoResponse;
import ecom.merce.ecommerce.entity.Pagamento;
import lombok.experimental.UtilityClass;

@UtilityClass
public class PagamentoMapper {

    public Pagamento toEntity(PagamentoRequest pagamentoRequest) {
        Pagamento pagamento = new Pagamento();
        pagamento.setFormaDePagamento(pagamentoRequest.formaPagamento());
        pagamento.setValor(pagamentoRequest.valor());
        pagamento.setStatusPagamento(pagamentoRequest.statusPagamento());
        pagamento.setDataPagamento(pagamentoRequest.dataPagamento());
        return pagamento;
    }

    public PagamentoResponse toDTO(Pagamento pagamento) {
        return new PagamentoResponse(
                pagamento.getId(), pagamento.getPedido().getId(), pagamento.getFormaDePagamento(), pagamento.getValor(),
                pagamento.getStatusPagamento(), pagamento.getDataPagamento()
        );
    }

}
