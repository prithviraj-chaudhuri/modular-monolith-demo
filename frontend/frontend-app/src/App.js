import React, { useState, useEffect } from 'react';
import './App.css';

const API_BASE = 'http://localhost:8080';

function App() {
  return (
    <div className="App">
      <h1>Modular Monolith Demo</h1>
      <Orders />
      <Inventory />
    </div>
  );
}

function Orders() {
  const [orders, setOrders] = useState([]);
  const [itemId, setItemId] = useState('');
  const [quantity, setQuantity] = useState('');

  useEffect(() => {
    fetchOrders();
  }, []);

  const fetchOrders = async () => {
    const response = await fetch(`${API_BASE}/orders`);
    const data = await response.json();
    setOrders(data);
  };

  const createOrder = async () => {
    await fetch(`${API_BASE}/orders?itemId=${itemId}&quantity=${quantity}`, {
      method: 'POST'
    });
    setItemId('');
    setQuantity('');
    fetchOrders();
  };

  return (
    <div className="section">
      <h2>Orders</h2>
      <div>
        <input
          type="number"
          placeholder="Item ID"
          value={itemId}
          onChange={(e) => setItemId(e.target.value)}
        />
        <input
          type="number"
          placeholder="Quantity"
          value={quantity}
          onChange={(e) => setQuantity(e.target.value)}
        />
        <button onClick={createOrder}>Create Order</button>
      </div>
      <ul>
        {orders.map(order => (
          <li key={order.id}>
            Order {order.id}: Item {order.itemId}, Quantity {order.quantity}, Status {order.status}
          </li>
        ))}
      </ul>
    </div>
  );
}

function Inventory() {
  const [items, setItems] = useState([]);
  const [name, setName] = useState('');
  const [count, setCount] = useState('');
  const [editingId, setEditingId] = useState(null);

  useEffect(() => {
    fetchItems();
  }, []);

  const fetchItems = async () => {
    const response = await fetch(`${API_BASE}/inventory`);
    const data = await response.json();
    setItems(data);
  };

  const createItem = async () => {
    await fetch(`${API_BASE}/inventory?name=${name}&count=${count}`, {
      method: 'POST'
    });
    setName('');
    setCount('');
    fetchItems();
  };

  const updateItem = async (id) => {
    await fetch(`${API_BASE}/inventory/${id}?name=${name}&count=${count}`, {
      method: 'PUT'
    });
    setName('');
    setCount('');
    setEditingId(null);
    fetchItems();
  };

  const deleteItem = async (id) => {
    await fetch(`${API_BASE}/inventory/${id}`, {
      method: 'DELETE'
    });
    fetchItems();
  };

  const startEdit = (item) => {
    setEditingId(item.id);
    setName(item.name);
    setCount(item.count);
  };

  return (
    <div className="section">
      <h2>Inventory</h2>
      <div>
        <input
          type="text"
          placeholder="Name"
          value={name}
          onChange={(e) => setName(e.target.value)}
        />
        <input
          type="number"
          placeholder="Count"
          value={count}
          onChange={(e) => setCount(e.target.value)}
        />
        {editingId ? (
          <button onClick={() => updateItem(editingId)}>Update Item</button>
        ) : (
          <button onClick={createItem}>Create Item</button>
        )}
        {editingId && <button onClick={() => setEditingId(null)}>Cancel</button>}
      </div>
      <ul>
        {items.map(item => (
          <li key={item.id}>
            {item.name}: {item.count}
            <button onClick={() => startEdit(item)}>Edit</button>
            <button onClick={() => deleteItem(item.id)}>Delete</button>
          </li>
        ))}
      </ul>
    </div>
  );
}

export default App;
