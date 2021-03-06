import java.util.*;

/*
 * This Java source file was generated by the Gradle 'init' task.
 */
public class App {
    private ItemRepository itemRepository;
    private SalesPromotionRepository salesPromotionRepository;

    public App(ItemRepository itemRepository, SalesPromotionRepository salesPromotionRepository) {
        this.itemRepository = itemRepository;
        this.salesPromotionRepository = salesPromotionRepository;
    }

    public String bestCharge(List<String> inputs) {
        //TODO: write code here
        LinkedHashMap<String, Integer> order = new LinkedHashMap<>();
        HashMap<String, Double> itemNameAndPrice = new HashMap<>(16);
        HashMap<String, String> itemAndName = new HashMap<>(16);
        for (String input : inputs) {
            String[] in = input.split(" ");
            order.put(in[0], Integer.parseInt(in[2]));
        }
        List<SalesPromotion> salesPromotions = this.salesPromotionRepository.findAll();
        List<Item> items = this.itemRepository.findAll();
        List<String> temp = salesPromotions.get(1).getRelatedItems();
        HashSet<String> promotions = new HashSet<>(temp);
        ArrayList<String> itemNameHalfPrice = new ArrayList<>();
        for (Item item : items) {
            itemNameAndPrice.put(item.getId(), item.getPrice());
            itemAndName.put(item.getId(), item.getName());
        }
        ArrayList<Integer> prices = new ArrayList<>();
        double sum = 0;
        double sum2 = 0;
        double moneySave = 0;
        ArrayList<String> res = new ArrayList<>();
        res.add("============= Order details =============\n");
        for (Map.Entry<String, Integer> o : order.entrySet()) {
            String key = o.getKey();
            Integer value = o.getValue();
            Double price = itemNameAndPrice.get(key);
            double truePrice = price * value;
            sum += truePrice;
            String name = itemAndName.get(key);
            if (promotions.contains(o.getKey())) {
                sum2 += 0.5 * truePrice;
                moneySave += 0.5 * truePrice;
                itemNameHalfPrice.add(name);
            } else {
                sum2 += truePrice;
            }
            res.add(name + " x " + (int) value + " = " + (int) truePrice + " yuan\n");
        }
        res.add("-----------------------------------\n");
        double sum1;
        if (sum >= 30) {
            sum1 = sum - 6;
        } else {
            sum1 = sum;
        }

        if (sum2 < sum1) {
            res.add("Promotion used:\n");
            res.add("Half price for certain dishes (" + String.join("��", itemNameHalfPrice) + ")��saving " + (int) moneySave + " yuan\n");
            res.add("-----------------------------------\n");
            res.add("Total��" + (int) sum2 + " yuan\n");
            res.add("===================================");
        } else if (sum1 < sum) {
            res.add("Promotion used:\n");
            res.add("��30��6 yuan��saving 6 yuan\n");
            res.add("-----------------------------------\n");
            res.add("Total��" + (int) sum1 + " yuan\n");
            res.add("===================================");
        } else {
            res.add("Total��" + (int) sum + " yuan\n");
            res.add("===================================");
        }
        return String.join("", res);
    }
}
