import React  from 'react';


function Adopt() {
  return (
    <>
        <div className='row align-items-right' >
            <ul className="nav col-12 col-lg-auto me-lg-auto mb-2 justify-content-center mb-md-0">
                <li class="nav-item">
                <input>
                </input>
                <button>SEARCH

                </button>
                </li>
            
                <li class="nav-item">Filters<i class="bi bi-filter-square "></i></li>
            
                <li class="nav-item">Sort by<i class="bi bi-filter-left"></i></li>
            </ul>
            </div>
            <div>
            <ul className="nav col-12 col-lg-auto me-lg-auto mb-2 justify-content-right mb-md-4">
            <li class="nav-item">
                <button>DOGS</button>
                </li>
                <li class="nav-item">
                <button>CATS</button>
                </li>
                <li class="nav-item">
                <button>RABBITS</button>
                </li>
            </ul>

            </div>
            <div>
            <div class="album py-5 bg-light">
                <div class="container">
                <div class="row row-cols-1 row-cols-sm-2 row-cols-md-3 g-3">
                    <div class="col">
                    <div class="card shadow-sm">
                        <svg class="bd-placeholder-img card-img-top" width="100%" height="225" xmlns="http://www.w3.org/2000/svg" role="img" aria-label="Placeholder: Thumbnail" preserveAspectRatio="xMidYMid slice" focusable="false"><title>Placeholder</title><rect width="100%" height="100%" fill="#55595c"></rect><text x="50%" y="50%" fill="#eceeef" dy=".3em">Thumbnail</text></svg>
                        <div class="card-body">
                        <ul>
                        <li>Name:</li>
                        <li>Age:</li>
                        <li>Provenance:</li>
                        </ul>
                        <div class="d-flex justify-content-between align-items-center">
                            <div class="btn-group">
                            <button type="button" class="btn btn-sm btn-outline-secondary">View</button>
                            </div>
                        </div>
                        </div>
                    </div>
                    </div>
                </div>
                </div>
            </div>
            </div>
    </>

  );
}

export default Adopt;