import React from 'react';
import ReactDOM from 'react-dom/client';
import {RouterProvider} from 'react-router-dom';
import {RecoilRoot} from 'recoil';
import Router from './Router';
import GlobalStyle from './styles/globalStyle';
import {NormalizeCSS} from '@mantine/core';

const root = ReactDOM.createRoot(
    document.getElementById('root') as HTMLElement
);

root.render(
    <React.StrictMode>
        <RecoilRoot>
            <NormalizeCSS/>
            <GlobalStyle/>
            <RouterProvider router={Router}/>
        </RecoilRoot>
    </React.StrictMode>
);
