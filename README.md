Particle Physics System for Processing

<hr />

Still working on examples. API is almost there.

<hr />

<h2>Features:</h2>
<ul>
   <li>internally uses PVector so no more wasteful vector conversion</li>
   <li>RK4 and Euler integrators</li>
   <li>Spring, Magnetic, ConstantGravitational, and Attractive forces</li>
   <li>plug in your own integrators and Forces</li>
   <li>uses a base Particle class for easy extenisbility</li>
   <li>examples provided</li>
</ul>

<h2>Binaries && Installation</h2>

Download the latest binaries here:

<a href="http://processing.datasingularity.com/p5particles/p5particles.zip">p5particles.zip</a>

Unzip and place the <i>p5particles</i> directory in your Processing <i>libraries</i> folder.

For more info see <a href="http://www.learningprocessing.com/tutorials/libraries/">Daniel Shiffman's page</a> on the subject of installing libraries.

<hr />

<h2>Examples</h2>

I have a number a few examples so far. Here are the links to the live applets. I have had some issues viewing them in Chrome, they seem to work best in Firefox from what I have tested.

<ul>
  <li><a href="http://processing.datasingularity.com/p5particles/examples/AttractiveForceExample/applet/">Attractive Forces</a></li>
  <li><a href="http://processing.datasingularity.com/p5particles/examples/RepulsiveForceExample/applet/">Repulsive Forces</a></li>
  <li><a href="http://processing.datasingularity.com/p5particles/examples/SpringForceExample/applet/">Spring Forces</a></li>
  <li><a href="http://processing.datasingularity.com/p5particles/examples/MagneticForceExample/applet/">Magnetic Forces</a></li>
  <li><a href="http://processing.datasingularity.com/p5particles/examples/KillingParticlesExample/applet/">Particle Lifecycle</a></li>
</ul>

The library comes with the examples so you can always open them locally.

<hr />

<h2>Usage && Documentation</h2>

The documentation, as it stands, constists of the examples and these javadocs which are quite sparse:

<a href="http://processing.datasingularity.com/p5particles/doc">http://processing.datasingularity.com/p5particles/doc/</a>

You will want to become very familiar with the <a href="http://processing.datasingularity.com/p5particles/doc/com/datasingularity/processing/p5particles/Particle.html">Particle class</a>. 

For instance, here is how we can create a particle that is rendered as a colored box of specified size:

<a href="https://gist.github.com/772055">Particle_extension.java</a>

<hr />

<h2>Performance && Tips</h2>

If you are going to be adding and removing many particles or forces throughout the lifetime of the sketch, use ParticleSystem::setMaxForces() and ParticleSystem::setMaxParticles(). This isn't actually a maximum, it just allocates that much memory ahead of time so adding a force or particle does not result in a copy of the array. All you need to do is take a guess at the maximum. This could be really useful for starting up an application. It internally calls <a href="http://download.oracle.com/javase/1.4.2/docs/api/java/util/ArrayList.html">ArrayList::ensureCapacity</a> so look there for more info.

The calculations themselves aren't too expensive. setting the ParticleSystem::setManagingLifecycle(true) can cause a slight performance drop, but for the most part, you are limited by rendering. Stay away from iterating over Particles or Forces when you don't have to, keep local copies of Particles or Forces that you want to manipulate often. 
