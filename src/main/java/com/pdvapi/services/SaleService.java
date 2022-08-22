package com.pdvapi.services;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.stream.Collectors;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.pdvapi.dtos.ItemSaleDTO;
import com.pdvapi.dtos.ProductDTO;
import com.pdvapi.dtos.ProductInfoDTO;
import com.pdvapi.dtos.SaleDTO;
import com.pdvapi.entities.ItemSale;
import com.pdvapi.entities.Product;
import com.pdvapi.entities.Sale;
import com.pdvapi.entities.User;
import com.pdvapi.exceptions.NoItemException;
import com.pdvapi.repositories.ItemSaleRepository;
import com.pdvapi.repositories.ProductRepository;
import com.pdvapi.repositories.SaleRepository;
import com.pdvapi.repositories.UserRepository;

@Service
public class SaleService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private SaleRepository saleRepository;

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private ItemSaleRepository itemSaleRepository;

    @Transactional
    public Long save(SaleDTO sale) throws Exception {
        User user = userRepository.findById(sale.getUserId()).get();

        Sale newSale = new Sale();
        newSale.setDate(LocalDate.now());
        newSale.setUser(user);
        List<ItemSale> items = getItemsSale(sale.getItems());
        newSale = saleRepository.save(newSale);

        saveItem(items, newSale);

        return newSale.getId();
    }

    public List<ItemSaleDTO> findAll() {
        return saleRepository.findAll().stream().map(sale -> getItemSale(sale)).collect(Collectors.toList());
    }

    private ItemSaleDTO getItemSale(Sale sale) {
        ItemSaleDTO itemSaleDTO = new ItemSaleDTO();
        itemSaleDTO.setUser(sale.getUser().getName());
        itemSaleDTO.setDate(sale.getDate().format(DateTimeFormatter.ofPattern("dd/MM/yyyy")));
        itemSaleDTO.setProducts(getProductInfo(sale.getItems()));

        return itemSaleDTO;
    }

    private List<ProductInfoDTO> getProductInfo(List<ItemSale> items) {
        return items.stream().map(item -> {
            ProductInfoDTO productInfoDTO = new ProductInfoDTO();
            productInfoDTO.setDescription(item.getProduct().getDescription());
            productInfoDTO.setQuantity(item.getQuantity());
            return productInfoDTO;
        }).collect(Collectors.toList());
    }

    private void saveItem(List<ItemSale> items, Sale newSale) {
        for (ItemSale item : items) {
            item.setSale(newSale);
            itemSaleRepository.save(item);
        }
    }

    private List<ItemSale> getItemsSale(List<ProductDTO> products) throws Exception {
        return products.stream().map(item -> {
            Product product = productRepository.getReferenceById(item.getProductId());
            ItemSale itemSale = new ItemSale();
            itemSale.setProduct(product);
            itemSale.setQuantity(item.getQuantity());

            if (product.getQuantity() == 0 || product.getQuantity() < item.getQuantity()) {
                throw new NoItemException(String.format("Estoque indisponível. Quantidade atual: %s.", product.getQuantity()));
            } 
            
            int total = product.getQuantity() - item.getQuantity();
            product.setQuantity(total);
            productRepository.save(product);

            return itemSale;
        }).collect(Collectors.toList());
    }

    public ItemSaleDTO getById(Long id) {
        Sale sale = saleRepository.findById(id).get();
        return getItemSale(sale);
    }
}
