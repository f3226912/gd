package com.gudeng.commerce.left_right;

import java.util.ArrayList;
import java.util.List;

import com.gudeng.commerce.gd.supplier.dto.ProductCategoryDTO;

public class LeftRightGenerator {

	/**
	 * 已经添加的菜单(按照菜单顺序添加)
	 */
	public static List<ProductCategoryDTO> increased = new ArrayList<ProductCategoryDTO>();
	public static int TOP_LEFT = 1;
	public static int TOP_RIGHT = 2;
	static {
		
		ProductCategoryDTO category = new ProductCategoryDTO();
		//注意此处id的设置, 只能为-1
		category.setCategoryId(-1l);
		category.setParentId(-2l);
		category.setCateName("白沙洲市场特级分类");
		category.setTypeIcon("#");
		category.setLefts(TOP_LEFT);
		category.setRights(TOP_RIGHT);
		increased.add(category);
		
	}

	public static List<ProductCategoryDTO> init(){
		List<ProductCategoryDTO> categorys = new ArrayList<ProductCategoryDTO>();
		
		ProductCategoryDTO category = new ProductCategoryDTO();
		category.setCategoryId(1l);
		category.setParentId(0l);
		category.setCateName("报表查询");
		category.setTypeIcon("#");
		categorys.add(category);

		category = new ProductCategoryDTO();
		category.setCategoryId(2l);
		category.setParentId(1l);
		category.setCateName("月报");
		category.setTypeIcon("#");
		categorys.add(category);

		category = new ProductCategoryDTO();
		category.setCategoryId(3l);
		category.setParentId(1l);
		category.setCateName("季报");
		category.setTypeIcon("#");
		categorys.add(category);

		category = new ProductCategoryDTO();
		category.setCategoryId(4l);
		category.setParentId(1l);
		category.setCateName("年报");
		category.setTypeIcon("#");
		categorys.add(category);

		category = new ProductCategoryDTO();
		category.setCategoryId(5l);
		category.setParentId(0l);
		category.setCateName("系统管理");
		category.setTypeIcon("#");
		categorys.add(category);

		category = new ProductCategoryDTO();
		category.setCategoryId(6l);
		category.setParentId(5l);
		category.setCateName("用户管理");
		category.setTypeIcon("#");
		categorys.add(category);

		category = new ProductCategoryDTO();
		category.setCategoryId(7l);
		category.setParentId(6l);
		category.setCateName("新增用户");
		category.setTypeIcon("#");
		categorys.add(category);

		category = new ProductCategoryDTO();
		category.setCategoryId(8l);
		category.setParentId(6l);
		category.setCateName("删除用户");
		category.setTypeIcon("#");
		categorys.add(category);

		category = new ProductCategoryDTO();
		category.setCategoryId(9l);
		category.setParentId(5l);
		category.setCateName("角色管理");
		category.setTypeIcon("#");
		categorys.add(category);
		
		category = new ProductCategoryDTO();
		category.setCategoryId(10l);
		category.setParentId(9l);
		category.setCateName("新增角色");
		category.setTypeIcon("#");
		categorys.add(category);

		category = new ProductCategoryDTO();
		category.setCategoryId(11l);
		category.setParentId(9l);
		category.setCateName("删除角色");
		category.setTypeIcon("#");
		categorys.add(category);
		return categorys;
	}

	/**
	 * 判断待添加的菜单与前一个菜单(即已经添加的菜单中的最后一个菜单)的关系
	 * 其关系要么是父子关系, 要么是兄弟关系, 要么是...
	 * @param category
	 * @return 1-父子关系, 2-兄弟关系, 3-{省略...},0-关系错误
	 */
	private int parentOrSibling(ProductCategoryDTO category){
		ProductCategoryDTO preCategory = getPreProductCategoryDTO();
		//父子
		if (category.getParentId().longValue() == preCategory.getCategoryId().longValue()){
			return 1 ;
		}else if (category.getParentId().longValue() == preCategory.getParentId().longValue()){//兄弟
			return 2 ;
		}
//		ProductCategoryDTO ancestor = getAncestorRecursion(preCategory, category.getParentId().longValue());
		ProductCategoryDTO ancestor = getAncestor(preCategory, category);
		if (ancestor != null){
			return 3 ;
		}
		//关系错误
		return 0;
	}

	public ProductCategoryDTO getAncestor(ProductCategoryDTO preCategory, ProductCategoryDTO category){
//		return getAncestorRecursion(preCategory, category.getParentId().longValue());//方式一
		return getAncestorRecursionWithPresent(preCategory, category);//方式二
	}
	/**
	 * 递归调用以获取preCategory的祖先(含父辈)中的某个分类, 该分类的父分类等于parentId
	 * @param preCategory
	 * @param parentId
	 * @return
	 */
	public ProductCategoryDTO getAncestorRecursion(ProductCategoryDTO preCategory, long parentId){
		ProductCategoryDTO parent = getParentCategory(preCategory);
		if (parent.getParentId().longValue() == parentId){
			return parent ;
		}else{
			return getAncestorRecursion(parent, parentId);
		}
	}

	public ProductCategoryDTO getAncestorRecursionWithPresent(ProductCategoryDTO preCategory, ProductCategoryDTO category){
		ProductCategoryDTO parent = getParentCategory(preCategory);
		if (parent.getParentId().longValue() == category.getParentId().longValue()){
			return parent;
		}else{
			return getAncestorRecursionWithPresent(parent, category);
		}
	}
	
	public ProductCategoryDTO getPreProductCategoryDTO(){
		return increased.get(increased.size()-1);
	}
	/**
	 * 查询边界值
	 * 待添加菜单与前一个菜单pre是父子关系, 则边界值是pre的左值
	 * 待添加菜单与前一个菜单pre是兄弟关系, 则边界值是pre的右值
	 * 待添加菜单与前一个菜单pre是...
	 * @param category
	 * @return
	 */
	public int lookforBoundary(ProductCategoryDTO category){
		int result = -1 ;
		int pos = parentOrSibling(category);
		ProductCategoryDTO preCategory = getPreProductCategoryDTO();
		if (pos == 1){
			result = preCategory.getLefts();
		}else if (pos == 2){
			result = preCategory.getRights();
		}else if (pos == 3){
			result = getAncestor(preCategory, category).getRights();
//			result = getAncestorRecursion(preCategory, category.getParentId().longValue()).getRights();
		}
		return result;
	}
	
	public ProductCategoryDTO getParentCategory(ProductCategoryDTO category){
		return getParentCategory(category.getCategoryId().longValue());
	}
	
	public ProductCategoryDTO getParentCategory(long categoryId){
		ProductCategoryDTO category = getCategory(categoryId);
		return getCategory(category.getParentId().longValue());
	}

	public ProductCategoryDTO getCategory(long categoryId){
		for(int i = 0, len = increased.size(); i < len; i++){
			if (categoryId == increased.get(i).getCategoryId().longValue()){
				return increased.get(i) ;
			}
		}
		return null;
	}
	
	public void addItemAndFillRightLeft(ProductCategoryDTO category){
		
/*		//如下写法可以去掉特级分类, 即increased默认不添加特级分类元素
		int bundary = 0;
		if (!increased.isEmpty()){
			bundary = lookforBoundary(category);
		}*/
		//此处写法要求increased默认添加特级分类元素
		int bundary = lookforBoundary(category);
		ProductCategoryDTO current = null ;
		for(int i = 0, len = increased.size(); i < len; i++){
			current = increased.get(i);
			if (current.getLefts() > bundary){
				current.setLefts(current.getLefts()+2);
			}
			if (current.getRights() > bundary){
				current.setRights(current.getRights() +2);
			}
		}
		category.setLefts(bundary+1);
		category.setRights(bundary+2);
		increased.add(category);
	}

	public void generateUpdateStatment(){
		StringBuffer sb = new StringBuffer();
		ProductCategoryDTO current = null ;
		for(int i = 0, len = increased.size(); i < len; i++){
			sb.setLength(0);
			printLineNumber(i+1);
			current = increased.get(i);
			sb.append("update product_category set lefts = " + current.getLefts() + ", rights = " + current.getRights() +
					", cateName = '" + current.getCateName() +	"' where categoryId = " + current.getCategoryId().longValue() + ";");
			System.out.println(sb.toString());
		}
	}
	public void printLineNumber(int lineNumber){
//		System.out.println("current is line " + lineNumber);
	}
	public void printMessageAfterUpdate(){
		ProductCategoryDTO current = null ;
		for(int i = 0, len = increased.size(); i < len; i++){
			current = increased.get(i);
			System.out.println( current.getCateName() + ", left : " + current.getLefts() + ", right : " + current.getRights());
		}
	}
	
	public void handle(List<ProductCategoryDTO> initList){
		for(int i = 0, len = initList.size(); i < len; i++){
			addItemAndFillRightLeft(initList.get(i));
		}
		generateUpdateStatment();
	}
	public static void main(String[] args) {
		LeftRightGenerator generator = new LeftRightGenerator();
		List<ProductCategoryDTO> initList = init();
		generator.handle(initList);
	}
}
