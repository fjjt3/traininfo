package com.traininfo.domain.port;

import com.traininfo.domain.model.Journey;
import com.traininfo.domain.model.TicketOffer;

import java.util.List;

public interface TicketOfferSourcePort {

    List<TicketOffer> findOffersFor(Journey journey);
}
