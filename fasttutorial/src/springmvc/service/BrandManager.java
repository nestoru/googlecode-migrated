package springmvc.service;
 
import java.util.LinkedList;
import java.util.List;
 
import springmvc.model.Brand;
 
public class BrandManager {
 
	private List<Brand> brandList; 
 
	public Brand getBrandById(Long id) {
		for (Brand brand : brandList) {
			if (brand.getId().equals(id))
				return brand;
		}
		return null;
	}
	
	public List<Brand> getBrandList() {
		return brandList;
	}	
 
	public void setBrandList(List<Brand> brandList) {
		this.brandList = brandList;
	}
}