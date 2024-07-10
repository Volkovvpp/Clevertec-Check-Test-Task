package main.java.ru.clevertec.check.factory;

import main.java.ru.clevertec.check.products.*;

public class ProductFactory {
    public Product getProduct(ProductType productType) {
        Product product = null;
        switch (productType) {
            case Milk:
                product = new Milk();
                break;
            case Cream400g:
                product = new Cream();
                break;
            case Yogurt400g:
                product = new Yogurt();
                break;
            case Packedpotatoes1kg:
                product = new Potato();
                break;
            case Packedcabbage1kg:
                product = new Cabbage();
                break;
            case Packedtomatoes350g:
                product = new Tomato();
                break;
            case Packedapples1kg:
                product = new Apple();
                break;
            case Packedoranges1kg:
                product = new Orange();
                break;
            case Packedbananas1kg:
                product = new Banana();
                break;
            case Packedbeeffillet1kg:
                product = new Beef();
                break;
            case Packedporkfillet1kg:
                product = new Pork();
                break;
            case Packedchickenbreasts1kgSour:
                product = new Chicken();
                break;
            case Baguette360g:
                product = new Baguette();
                break;
            case Drinkingwater15l:
                product = new Water();
                break;
            case Oliveoil500ml:
                product = new OliveOil();
                break;
            case Sunfloweroil1l:
                product = new SunflowerOil();
                break;
            case ChocolateRittersport100g:
                product = new Chocolate();
                break;
            case Paulaner05l:
                product = new Paulaner();
                break;
            case WhiskeyJimBeam1l:
                product = new JimBeam();
                break;
            case WhiskeyJackDaniels1l:
                product = new JackDaniels();
                break;
            default: throw new IllegalArgumentException("Wrong type" + productType);
        }
        return product;
    }
}
