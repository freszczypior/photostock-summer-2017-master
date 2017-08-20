package pl.com.bottega.photostock.sales.ui;

import pl.com.bottega.photostock.sales.model.*;

import java.util.HashSet;
import java.util.Set;

public class ConsoleApp {

    public static void main(String[] args) {

        Set<String> tags = new HashSet<>();
        tags.add("Kotki");
        Picture picture1 = new Picture(1L, tags, Money.valueOf(10));
        Picture picture2 = new Picture(2L, tags, Money.valueOf(5));
        Picture picture3 = new Picture(3L, tags, Money.valueOf(15));

        Client client = new Client("Jan Nowak", new Address("ul. Północna 11", "Polska", "Lublin", "20-001"));
        client.reCharge(Money.valueOf(30));

        Reservation reservation = new Reservation(client);
        LightBox lightBox = new LightBox(client, "Kotki");

        lightBox.add(picture1);
        lightBox.add(picture2);
        lightBox.add(picture3);

        LightboxPresenter lightboxPresenter = new LightboxPresenter();
        lightboxPresenter.showLightbox(lightBox);

        reservation.add(picture1);
        reservation.add(picture2);
        reservation.add(picture3);

//        reservation.add(picture1);
//        reservation.add(picture2);
//        reservation.add(picture3);

        System.out.println(String.format("W rezerwacji jest %d produktów.", reservation.getItemCount()));   // "%d" (d dla liczb, dla stringów %s) jest tutaj znakiem specjalnym, pod niego podstawiamy konkretną wartość

        Offer offer = reservation.generateOffer();
        Money cost = offer.getTotalCost();      // dla ułatwienia, aby móc później kilka razy ją wywołać

        if (client.canAfford(cost)) {
            Purchase purchase = new Purchase(client, offer.getItems());
            client.charge(cost, String.format("Zakup %s.", purchase));
            System.out.println(String.format("Ilość zakupionych zdjęć: %d, całkowity koszt %s.", offer.getItemCount(), offer.getTotalCost()));
        } else
            System.out.println("Hehehe, nie stać Cię!");

    }
}