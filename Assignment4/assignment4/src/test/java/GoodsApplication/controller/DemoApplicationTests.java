package GoodsApplication.controller;

import GoodsApplication.entity.Goods;
import GoodsApplication.service.GoodsService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

import java.util.ArrayList;
import java.util.List;

import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@SpringBootTest
@AutoConfigureMockMvc
@EnableWebMvc
class DemoApplicationTests {

	@Autowired
	private MockMvc mockMvc;

	/*
	使用MockBean来替换真实的ProductService，目的是只关注与ProductController部分代码的测试，而不测试ProductService的代码
	 */
	@MockBean
	private GoodsService goodsService;

	@Test
	void getGoods_ValidId_ReturnsGoods() throws Exception{
		String id="1";
		Goods expectedGoods=new Goods();
		expectedGoods.setId(id);
		when(goodsService.QueryById(id)).thenReturn(expectedGoods);

		mockMvc.perform(MockMvcRequestBuilders.get("/goods/{id}",id))
				.andExpect(MockMvcResultMatchers.status().isOk())
				.andExpect(MockMvcResultMatchers.jsonPath("$.id").value(id));
	}

	@Test
	void getGoods_InvalidId_ReturnsNoContent() throws Exception{
		String id="1";
		when(goodsService.QueryById(id)).thenReturn(null);

		mockMvc.perform(MockMvcRequestBuilders.get("/goods/{id}"))
				.andExpect(MockMvcResultMatchers.status().isNoContent());
	}

	@Test
	void findGoods_ReturnsListofProducts() throws Exception{
		String goodsName="Test Goods";
		Float goodsQuantity=10.0f;
		List<Goods> expectedProducts=new ArrayList<>();
		expectedProducts.add(new Goods());
		when(goodsService.findGoods(goodsName,goodsQuantity)).thenReturn(expectedProducts);

		mockMvc.perform(MockMvcRequestBuilders.get("/goods")
					.param("name",goodsName)
					.param("quantity",goodsQuantity.toString()))
				.andExpect(MockMvcResultMatchers.status().isOk())
				.andExpect(MockMvcResultMatchers.jsonPath("$[0]").exists());
	}

	@Test
	void addGoods_ValidGoods_ReturnGoods() throws Exception{
		Goods inputGoods=new Goods();
		inputGoods.setName("Test Goods");
		inputGoods.setPrice(10);

		mockMvc.perform(MockMvcRequestBuilders.post("/goods/add")
				.contentType(MediaType.APPLICATION_JSON)
				.content(new ObjectMapper().writeValueAsString(inputGoods)))
				.andExpect(MockMvcResultMatchers.status().isOk());

		//验证goodsService调用参数是否正确
		verify(goodsService).AddGood(inputGoods);
	}
	@Test
	void contextLoads() {
	}

}
