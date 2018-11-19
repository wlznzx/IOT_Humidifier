package cn.alauncher.iot.humidifier.modle;


/*
	drop table User;
	create table if not exists HumidifierInfo(id int auto_increment primary key,temperature int,humidity int);
*/
public class HumidifierInfo {

	public int id;
	public int temperature;
	public int humidity;

	public HumidifierInfo(int id, int temperature, int humidity) {
		super();
		this.id = id;
		this.temperature = temperature;
		this.humidity = humidity;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public int getTemperature() {
		return temperature;
	}

	public void setTemperature(int temperature) {
		this.temperature = temperature;
	}

	public int getHumidity() {
		return humidity;
	}

	public void setHumidity(int humidity) {
		this.humidity = humidity;
	}

	@Override
	public String toString() {
		return "HumidifierInfo [id=" + id + ", temperature=" + temperature + ", humidity=" + humidity + "]";
	}

}
