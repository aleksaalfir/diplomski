import './App.css';
import {BrowserRouter, Route, Routes} from "react-router-dom";
import routeService from "./services/api/route-service";

function App() {

    const allowedRoutes = routeService.getAllowedRoutes();
    const redirect = routeService.getRedirect();

  return (
    <BrowserRouter>
        <Routes>
            {allowedRoutes.map((route, index)=>{
                return <Route key={index} path={route.path} element={route.element} exact/>
            })}
            {redirect}
        </Routes>
    </BrowserRouter>
  );
}

export default App;
