package no.hvl.dat110.broker;

import java.util.Collection;
import java.util.HashSet;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

import no.hvl.dat110.messagetransport.Connection;

public class Storage {

	// data structure for managing subscriptions
	// maps from a topic to set of subscribed users
	protected ConcurrentHashMap<String, Set<String>> subscriptions;
	
	// data structure for managing currently connected clients
	// maps from user to corresponding client session object
	
	protected ConcurrentHashMap<String, ClientSession> clients;

	public Storage() {
		subscriptions = new ConcurrentHashMap<String, Set<String>>();
		clients = new ConcurrentHashMap<String, ClientSession>();
	}

	public Collection<ClientSession> getSessions() {
		return clients.values();
	}

	public Set<String> getTopics() {

		return subscriptions.keySet();

	}

	// get the session object for a given user
	// session object can be used to send a message to the user
	
	public ClientSession getSession(String user) {

		ClientSession session = clients.get(user);

		return session;
	}

	public Set<String> getSubscribers(String topic) {

		return (subscriptions.get(topic));

	}

	public void addClientSession(String user, Connection connection) {
		
		// Legge til i hashMap med sting(key) og opprette en client-session(value)
		
		clients.put(user, new ClientSession(user, connection));
	}

	public void removeClientSession(String user) {
		
		// Lete opp og fjerne en key, value fra hashMap med hjelp av user
		
		clients.remove(user);
	}

	public void createTopic(String topic) {
		
		// Legge til en topic (key) i subscriptions med ett tomt sett med brukere(value)
		
		subscriptions.put(topic, new HashSet<String>());

	}

	public void deleteTopic(String topic) {
		
		// Fjerne gitt topic fra subscriptions
		
		subscriptions.remove(topic);
		
	}

	public void addSubscriber(String user, String topic) {
		
		// Legge til en subscriber (user, value) i subscriptions ved hjelp av topic (key)
		
		subscriptions.get(topic).add(user);
	}

	public void removeSubscriber(String user, String topic) {
		
		// Fjerne en bruker (value) fra subscriptions ved hjelp av en bruker (key)
		
		subscriptions.get(topic).remove(user);
	}
}
