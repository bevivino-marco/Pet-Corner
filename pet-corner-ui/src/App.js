import logo from './logo.svg';
import React  from 'react';
import './App.css';
import Adopt from './adopt/adopt';

function App() {
  return (
    <>
    <header className='p-3'>
      <div className='container'>
        <div class="d-flex flex-wrap align-items-center justify-content-center justify-content-lg-start">
          <i>LOGO
            <svg></svg>
          </i>
          <ul className="nav col-12 col-lg-auto me-lg-auto mb-2 justify-content-center mb-md-0">
            <li class="nav-item"><a href="#" class="nav-link link-dark px-2 active " aria-current="page">Home</a></li>
            <li class="nav-item"><a href="#" class="nav-link link-dark px-2">Features</a></li>
            <li class="nav-item"><a href="#" class="nav-link link-dark px-2">Pricing</a></li>
            <li class="nav-item"><a href="#" class="nav-link link-dark px-2">FAQs</a></li>
            <li class="nav-item"><a href="#" class="nav-link link-dark px-2">About</a></li>
          </ul>
            <i class="bi bi-person-circle"></i>
        </div>
      </div>
    </header>

    <Adopt></Adopt>

  </>
  );
}

export default App;
