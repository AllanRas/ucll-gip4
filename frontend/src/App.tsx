import 'bootstrap/dist/css/bootstrap.css'
import './App.css';
import Header from "./Components/Header";
import {Route, BrowserRouter as Router, Routes} from "react-router-dom";
import { Container } from 'react-bootstrap'
import Spelers from "./Components/Spelers";

function App() {
  return (
      <Router>
          <div className="App">
              <Header />
              <Container>
                  <Routes>
                      <>
                          <Route path="/Spelers" element={<Spelers/>}/>
                      </>

                  </Routes>
              </Container>
          </div>
      </Router>
        );
    }

export default App;
