(ns tutorial.scrape1
  (:require [net.cgrand.enlive-html :as html]))

(def ^:dynamic *base-url* "http://www.ah.nl/allerhande/recepten/R-L1383828432046/kerstrecepten")

(defn fetch-url [url]
  (html/html-resource (java.net.URL. url)))

(defn allerhande-recepten []
  (html/select (fetch-url *base-url*) [:div.browse-links__lists.js-browse-links :ul :li :a]))

(defn hn-headlines []
  (map html/text (html/select (fetch-url *base-url*) [:td.title :a])))

(defn hn-points []
  (map html/text (html/select (fetch-url *base-url*) [:td.subtext html/first-child])))

(defn print-headlines-and-points []
  (doseq [line (map #(str %1 " (" %2 ")") (hn-headlines) (hn-points))]
    (println line)))
