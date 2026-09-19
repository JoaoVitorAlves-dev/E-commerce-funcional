package ecom.merce.ecommerce.domain.service;

import ecom.merce.ecommerce.domain.exceptions.IdNotFoundException;
import ecom.merce.ecommerce.domain.repository.PagamentoRepository;
import ecom.merce.ecommerce.dto.request.PagamentoRequest;
import ecom.merce.ecommerce.dto.response.PagamentoResponse;
import ecom.merce.ecommerce.entity.Pagamento;
import ecom.merce.ecommerce.mapper.PagamentoMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class PagamentoService {

    private final PagamentoRepository pagamentoRepository;

    public Page<PagamentoResponse> listar(@PageableDefault(sort = "valor", size = 10) Pageable pageable) {
        return pagamentoRepository.findByValor(pageable).map(PagamentoMapper::toDTO);
    }

    public PagamentoResponse buscarPorId(Long id) {
        Pagamento pagamento = pagamentoRepository.findById(id).orElseThrow(() -> new IdNotFoundException("ID Não existe"));
        return PagamentoMapper.toDTO(pagamento);
    }

    public PagamentoResponse adicionarPagamento(PagamentoRequest pagamentoRequest) {
        Pagamento save = pagamentoRepository.save(PagamentoMapper.toEntity(pagamentoRequest));
        return PagamentoMapper.toDTO(save);
    }

    public PagamentoResponse atualizarPorId(Long id, PagamentoRequest pagamentoRequest) {
        pagamentoRepository.findById(id).orElseThrow(() -> new IdNotFoundException("ID Não existe"));
        Pagamento entity = PagamentoMapper.toEntity(pagamentoRequest);
        entity.setId(id);
        Pagamento save = pagamentoRepository.save(entity);
        return PagamentoMapper.toDTO(save);
    }

    public void deletarPorId(Long id) {
        pagamentoRepository.deleteById(id);
    }


}
