package com.mixiusi.bean;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
public class Material implements Serializable{
	
	/**
	 * 1号咖啡豆盒：正常咖啡豆（缺料预警）
				2号咖啡豆盒：低因咖啡豆（缺料预警）
				3号粉料盒：抹茶粉（不作监测）
				4号粉料盒：可可粉（不作监测）
				5号液体盒：牛奶（缺料预警）
				6号液体盒：香草糖浆（不作监测）
				7号液体盒：榛果糖浆（不作监测）
				8号液体盒：焦糖糖浆（不作监测）
				9号液体盒：纯糖浆（不作监测）
				10号水盒：桶装水（缺料预警）
	 */
	private static final long serialVersionUID = 796004993114259575L;

	@Id
//	@Column(length = 50)
	private String machineId;
	/**
	 * 普通咖啡豆
	 */
	private Double coffeebean;
	/**
	 * 低因咖啡豆
	 */
	private Double lcoffeebean;
	/**
	 * 抹茶粉
	 */
	private Double maccha_powder;
	/**
	 * 可可粉
	 */
	private Double cocoa_powder;
	/**
	 * 牛奶
	 */
	private Double milk;
	/**
	 * 香草糖浆
	 */
	private Double vanilla_sugar;
	/**
	 * 榛果糖浆
	 */
	private Double hazelnut_sugar;
	/**
	 * 焦糖糖浆
	 */
	private Double caramel_sugar;
	/**
	 * 纯糖浆
	 */
	private Double pure_sugar;
	/**
	 * 水
	 */
	private Double water;
	/**
	 * 杯子
	 */
	private Double cupnum;
	public String getMachineId() {
		return machineId;
	}
	public Double getCoffeebean() {
		return coffeebean;
	}
	public Double getLcoffeebean() {
		return lcoffeebean;
	}
	public Double getMaccha_powder() {
		return maccha_powder;
	}
	public Double getCocoa_powder() {
		return cocoa_powder;
	}
	public Double getMilk() {
		return milk;
	}
	public Double getVanilla_sugar() {
		return vanilla_sugar;
	}
	public Double getHazelnut_sugar() {
		return hazelnut_sugar;
	}
	public Double getCaramel_sugar() {
		return caramel_sugar;
	}
	public Double getPure_sugar() {
		return pure_sugar;
	}
	public Double getWater() {
		return water;
	}
	public Double getCupnum() {
		return cupnum;
	}
	public void setMachineId(String machineId) {
		this.machineId = machineId;
	}
	public void setCoffeebean(Double coffeebean) {
		this.coffeebean = coffeebean;
	}
	public void setLcoffeebean(Double lcoffeebean) {
		this.lcoffeebean = lcoffeebean;
	}
	public void setMaccha_powder(Double maccha_powder) {
		this.maccha_powder = maccha_powder;
	}
	public void setCocoa_powder(Double cocoa_powder) {
		this.cocoa_powder = cocoa_powder;
	}
	public void setMilk(Double milk) {
		this.milk = milk;
	}
	public void setVanilla_sugar(Double vanilla_sugar) {
		this.vanilla_sugar = vanilla_sugar;
	}
	public void setHazelnut_sugar(Double hazelnut_sugar) {
		this.hazelnut_sugar = hazelnut_sugar;
	}
	public void setCaramel_sugar(Double caramel_sugar) {
		this.caramel_sugar = caramel_sugar;
	}
	public void setPure_sugar(Double pure_sugar) {
		this.pure_sugar = pure_sugar;
	}
	public void setWater(Double water) {
		this.water = water;
	}
	public void setCupnum(Double cupnum) {
		this.cupnum = cupnum;
	}
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((caramel_sugar == null) ? 0 : caramel_sugar.hashCode());
		result = prime * result + ((cocoa_powder == null) ? 0 : cocoa_powder.hashCode());
		result = prime * result + ((coffeebean == null) ? 0 : coffeebean.hashCode());
		result = prime * result + ((cupnum == null) ? 0 : cupnum.hashCode());
		result = prime * result + ((hazelnut_sugar == null) ? 0 : hazelnut_sugar.hashCode());
		result = prime * result + ((lcoffeebean == null) ? 0 : lcoffeebean.hashCode());
		result = prime * result + ((maccha_powder == null) ? 0 : maccha_powder.hashCode());
		result = prime * result + ((machineId == null) ? 0 : machineId.hashCode());
		result = prime * result + ((milk == null) ? 0 : milk.hashCode());
		result = prime * result + ((pure_sugar == null) ? 0 : pure_sugar.hashCode());
		result = prime * result + ((vanilla_sugar == null) ? 0 : vanilla_sugar.hashCode());
		result = prime * result + ((water == null) ? 0 : water.hashCode());
		return result;
	}
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Material other = (Material) obj;
		if (caramel_sugar == null) {
			if (other.caramel_sugar != null)
				return false;
		} else if (!caramel_sugar.equals(other.caramel_sugar))
			return false;
		if (cocoa_powder == null) {
			if (other.cocoa_powder != null)
				return false;
		} else if (!cocoa_powder.equals(other.cocoa_powder))
			return false;
		if (coffeebean == null) {
			if (other.coffeebean != null)
				return false;
		} else if (!coffeebean.equals(other.coffeebean))
			return false;
		if (cupnum == null) {
			if (other.cupnum != null)
				return false;
		} else if (!cupnum.equals(other.cupnum))
			return false;
		if (hazelnut_sugar == null) {
			if (other.hazelnut_sugar != null)
				return false;
		} else if (!hazelnut_sugar.equals(other.hazelnut_sugar))
			return false;
		if (lcoffeebean == null) {
			if (other.lcoffeebean != null)
				return false;
		} else if (!lcoffeebean.equals(other.lcoffeebean))
			return false;
		if (maccha_powder == null) {
			if (other.maccha_powder != null)
				return false;
		} else if (!maccha_powder.equals(other.maccha_powder))
			return false;
		if (machineId == null) {
			if (other.machineId != null)
				return false;
		} else if (!machineId.equals(other.machineId))
			return false;
		if (milk == null) {
			if (other.milk != null)
				return false;
		} else if (!milk.equals(other.milk))
			return false;
		if (pure_sugar == null) {
			if (other.pure_sugar != null)
				return false;
		} else if (!pure_sugar.equals(other.pure_sugar))
			return false;
		if (vanilla_sugar == null) {
			if (other.vanilla_sugar != null)
				return false;
		} else if (!vanilla_sugar.equals(other.vanilla_sugar))
			return false;
		if (water == null) {
			if (other.water != null)
				return false;
		} else if (!water.equals(other.water))
			return false;
		return true;
	}
	@Override
	public String toString() {
		return "Material [machineId=" + machineId + ", coffeebean=" + coffeebean + ", lcoffeebean=" + lcoffeebean
				+ ", maccha_powder=" + maccha_powder + ", cocoa_powder=" + cocoa_powder + ", milk=" + milk
				+ ", vanilla_sugar=" + vanilla_sugar + ", hazelnut_sugar=" + hazelnut_sugar + ", caramel_sugar="
				+ caramel_sugar + ", pure_sugar=" + pure_sugar + ", water=" + water + ", cupnum=" + cupnum + "]";
	}
	
}
