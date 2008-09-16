package springmvc.service;
 
import java.math.BigDecimal;
import java.util.LinkedList;
import java.util.List;
 
import springmvc.model.Brand;
import springmvc.model.Car;
 
public class CarManager {
 
	private List<Car> carList;


	public Car createCar(Car c) {
  	Car car = new Car();
  	car.setId((long)carList.size() + 1);
  	car.setBrand(c.getBrand());
  	car.setModel(c.getModel());
  	car.setPrice(c.getPrice());
   
  	carList.add(car);
   
  	return car;
  }
  
  public List<Car> getCarList() {
		return carList;
	}	
 
	public void setCarList(List<Car> carList) {
		this.carList = carList;
	}
 
}